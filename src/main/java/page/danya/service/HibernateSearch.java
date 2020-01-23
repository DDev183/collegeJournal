package page.danya.service;

import org.hibernate.search.FullTextQuery;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import page.danya.models.Teaching;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class HibernateSearch{

    @PersistenceContext
    private EntityManager entityManager;

    private FullTextQuery getJpaQuery(org.apache.lucene.search.Query luceneQuery) {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        return (FullTextQuery) fullTextEntityManager.createFullTextQuery(luceneQuery, Teaching.class);
    }

    private QueryBuilder getQueryBuilder() {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        return fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Teaching.class)
                .get();
    }

}
