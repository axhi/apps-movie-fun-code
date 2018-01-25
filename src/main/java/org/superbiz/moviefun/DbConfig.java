package org.superbiz.moviefun;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    @Bean
    public static HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");

        return hibernateJpaVendorAdapter;
    }

    @Configuration
    public static class Albums {
        @Value("${moviefun.datasources.albums.url}") String url;
        @Value("${moviefun.datasources.albums.username}") String username;
        @Value("${moviefun.datasources.albums.password}") String password;

        @Bean
        public DataSource albumsDataSource() {
            return getDataSource(url, username, password);
        }

        @Bean
        @Qualifier("albums")
        public LocalContainerEntityManagerFactoryBean albumsEntityManager() {
            return localContainerEntityManagerFactoryBean(albumsDataSource(),
                    hibernateJpaVendorAdapter(), "albums");
        }

        @Bean
        public PlatformTransactionManager albumsTransactionManager() {
            return new JpaTransactionManager(albumsEntityManager().getObject());
        }
    }

    @Configuration
    public static class Movies {
        @Value("${moviefun.datasources.movies.url}") String url;
        @Value("${moviefun.datasources.movies.username}") String username;
        @Value("${moviefun.datasources.movies.password}") String password;

        @Bean
        public DataSource moviesDataSource() {
            return getDataSource(url, username, password);
        }

        @Bean
        @Qualifier("movies")
        public LocalContainerEntityManagerFactoryBean moviesEntityManager() {
            return localContainerEntityManagerFactoryBean(moviesDataSource(),
                    hibernateJpaVendorAdapter(), "movies");
        }

        @Bean
        public PlatformTransactionManager moviesTransactionManager() {
            return new JpaTransactionManager(moviesEntityManager().getObject());
        }
    }

    private static DataSource getDataSource(String url, String username, String password) {
        return new HikariDataSource(hikariConfig(url, username, password));
    }

    private static HikariConfig hikariConfig(String url, String username, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        return new HikariDataSource(config);
    }

    private static LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(DataSource dataSource,
                                                                                                 HibernateJpaVendorAdapter hibernateJpaVendorAdapter,
                                                                                                 String unitName) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        factoryBean.setPackagesToScan(DbConfig.class.getPackage().getName());
        factoryBean.setPersistenceUnitName(unitName);

        return factoryBean;
    }
}