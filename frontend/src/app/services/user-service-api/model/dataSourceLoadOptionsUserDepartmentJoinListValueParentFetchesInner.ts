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
import { DataSourceLoadOptionsUserDepartmentJoinListValueParentFetchesInnerAttribute } from './dataSourceLoadOptionsUserDepartmentJoinListValueParentFetchesInnerAttribute';


export interface DataSourceLoadOptionsUserDepartmentJoinListValueParentFetchesInner { 
    parent?: object;
    attribute?: DataSourceLoadOptionsUserDepartmentJoinListValueParentFetchesInnerAttribute;
    joinType?: DataSourceLoadOptionsUserDepartmentJoinListValueParentFetchesInner.JoinTypeEnum;
}
export namespace DataSourceLoadOptionsUserDepartmentJoinListValueParentFetchesInner {
    export type JoinTypeEnum = 'INNER' | 'LEFT' | 'RIGHT';
    export const JoinTypeEnum = {
        Inner: 'INNER' as JoinTypeEnum,
        Left: 'LEFT' as JoinTypeEnum,
        Right: 'RIGHT' as JoinTypeEnum
    };
}

