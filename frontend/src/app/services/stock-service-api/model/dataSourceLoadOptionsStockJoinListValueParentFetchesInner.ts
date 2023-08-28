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
import { DataSourceLoadOptionsStockJoinListValueParentFetchesInnerAttribute } from './dataSourceLoadOptionsStockJoinListValueParentFetchesInnerAttribute';


export interface DataSourceLoadOptionsStockJoinListValueParentFetchesInner { 
    parent?: object;
    attribute?: DataSourceLoadOptionsStockJoinListValueParentFetchesInnerAttribute;
    joinType?: DataSourceLoadOptionsStockJoinListValueParentFetchesInner.JoinTypeEnum;
}
export namespace DataSourceLoadOptionsStockJoinListValueParentFetchesInner {
    export type JoinTypeEnum = 'INNER' | 'LEFT' | 'RIGHT';
    export const JoinTypeEnum = {
        Inner: 'INNER' as JoinTypeEnum,
        Left: 'LEFT' as JoinTypeEnum,
        Right: 'RIGHT' as JoinTypeEnum
    };
}


