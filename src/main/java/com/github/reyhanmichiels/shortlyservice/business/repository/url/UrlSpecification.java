package com.github.reyhanmichiels.shortlyservice.business.repository.url;

import com.github.reyhanmichiels.shortlyservice.business.dto.url.UrlParam;
import com.github.reyhanmichiels.shortlyservice.business.entity.Url;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UrlSpecification {

    public static Specification<Url> param(UrlParam param) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (param.getShortUrl() != null) {
                predicates.add(cb.equal(root.get("shortUrl"), param.getShortUrl()));
            }

            if (param.getIsActive() != null) {
                predicates.add(cb.equal(root.get("isActive"), param.getIsActive()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
