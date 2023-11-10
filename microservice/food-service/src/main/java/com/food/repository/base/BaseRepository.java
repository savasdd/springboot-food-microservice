package com.food.repository.base;

import com.food.data.options.DataSourceLoadOptionsBase;
import com.food.data.response.LoadResult;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends Repository<T, ID> {

    LoadResult load(DataSourceLoadOptionsBase options);

    LoadResult loadTuple(DataSourceLoadOptionsBase options);

    LoadResult loadEntity(DataSourceLoadOptionsBase options);

    String getSqlQuery(DataSourceLoadOptionsBase options);
}
