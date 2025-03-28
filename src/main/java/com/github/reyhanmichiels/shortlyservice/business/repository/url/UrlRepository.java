package com.github.reyhanmichiels.shortlyservice.business.repository.url;

import com.github.reyhanmichiels.shortlyservice.business.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UrlRepository extends JpaRepository<Url, Long>, JpaSpecificationExecutor<Url> {
}
