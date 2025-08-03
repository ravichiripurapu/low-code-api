package io.lowcode.platform.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import io.lowcode.platform.config.CacheProperties;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    @Autowired
    public CacheConfiguration(CacheProperties cacheProperties) {
        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                    Object.class,
                    Object.class,
                    ResourcePoolsBuilder.heap(cacheProperties.getMaxEntries())
                )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(cacheProperties.getTimeToLiveSeconds())))
                .build()
        );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, io.lowcode.platform.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, io.lowcode.platform.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, io.lowcode.platform.domain.User.class.getName());
            createCache(cm, io.lowcode.platform.domain.Authority.class.getName());
            createCache(cm, io.lowcode.platform.domain.Org.class.getName());
            createCache(cm, io.lowcode.platform.domain.PublicationStatus.class.getName());
            createCache(cm, io.lowcode.platform.domain.PublicationType.class.getName());
            createCache(cm, io.lowcode.platform.domain.PublicationForm.class.getName());
            createCache(cm, io.lowcode.platform.domain.PublicationFormData.class.getName());
            createCache(cm, io.lowcode.platform.domain.PublicationFormVersion.class.getName());
            createCache(cm, io.lowcode.platform.domain.Publication.class.getName());
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }


    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

}
