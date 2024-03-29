/**
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
import { PageableObject } from './pageableObject';
import { DataSourceGroup } from './dataSourceGroup';
import { DataSourceLoadOptionsStockJoinListValue } from './dataSourceLoadOptionsStockJoinListValue';
import { StoreSortView } from './storeSortView';
import { DataSourceGroupView } from './dataSourceGroupView';


/**
 * 
 */
export interface DataSourceLoadOptionsStock { 
    requireTotalCount?: boolean;
    searchOperation?: string;
    searchValue?: string;
    searchExpr?: string;
    page?: number;
    size?: number;
    userData?: object;
    stringId?: string;
    verifyToken?: string;
    id?: number;
    distinct?: boolean;
    sort?: Array<StoreSortView>;
    sortMock?: Array<StoreSortView>;
    filter?: Array<object>;
    select?: Array<string>;
    group?: Array<DataSourceGroup>;
    groupSummary?: Array<DataSourceGroupView>;
    joinList?: { [key: string]: DataSourceLoadOptionsStockJoinListValue; };
    pageable?: PageableObject;
    camelToSnake?: boolean;
    mockPageable?: PageableObject;
    sortable?: PageableObject;
}

