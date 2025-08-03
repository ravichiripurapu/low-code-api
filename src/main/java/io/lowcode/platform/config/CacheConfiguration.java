package io.lowcode.platform.config;

import java.time.Duration;

import io.lowcode.platform.domain.*;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

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

            createCache(cm, DocumentStatus.class.getName());
            createCache(cm, DocumentType.class.getName());
            createCache(cm, DocumentMetadata.class.getName());
            createCache(cm, DocumentData.class.getName());
            createCache(cm, DocumentMetadataVersion.class.getName());
            createCache(cm, Document.class.getName());


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
