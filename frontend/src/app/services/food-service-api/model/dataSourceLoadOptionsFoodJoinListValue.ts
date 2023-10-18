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
import { DataSourceLoadOptionsFoodJoinListValueParentParentPathCompoundSelectionItemsInner } from './dataSourceLoadOptionsFoodJoinListValueParentParentPathCompoundSelectionItemsInner';
import { DataSourceLoadOptionsFoodJoinListValueParent } from './dataSourceLoadOptionsFoodJoinListValueParent';
import { DataSourceLoadOptionsFoodJoinListValueParentFetchesInnerAttribute } from './dataSourceLoadOptionsFoodJoinListValueParentFetchesInnerAttribute';
import { DataSourceLoadOptionsFoodJoinListValueParentFetchesInner } from './dataSourceLoadOptionsFoodJoinListValueParentFetchesInner';
import { DataSourceLoadOptionsFoodJoinListValueParentParentPath } from './dataSourceLoadOptionsFoodJoinListValueParentParentPath';
import { DataSourceLoadOptionsFoodJoinListValueOn } from './dataSourceLoadOptionsFoodJoinListValueOn';
import { DataSourceLoadOptionsFoodJoinListValueParentModel } from './dataSourceLoadOptionsFoodJoinListValueParentModel';


export interface DataSourceLoadOptionsFoodJoinListValue { 
    parent?: DataSourceLoadOptionsFoodJoinListValueParent;
    attribute?: DataSourceLoadOptionsFoodJoinListValueParentFetchesInnerAttribute;
    on?: DataSourceLoadOptionsFoodJoinListValueOn;
    joinType?: DataSourceLoadOptionsFoodJoinListValue.JoinTypeEnum;
    correlationParent?: DataSourceLoadOptionsFoodJoinListValueParent;
    correlated?: boolean;
    model?: DataSourceLoadOptionsFoodJoinListValueParentModel;
    parentPath?: DataSourceLoadOptionsFoodJoinListValueParentParentPath;
    compoundSelection?: boolean;
    compoundSelectionItems?: Array<DataSourceLoadOptionsFoodJoinListValueParentParentPathCompoundSelectionItemsInner>;
    alias?: string;
    fetches?: Set<DataSourceLoadOptionsFoodJoinListValueParentFetchesInner>;
}
export namespace DataSourceLoadOptionsFoodJoinListValue {
    export type JoinTypeEnum = 'INNER' | 'LEFT' | 'RIGHT';
    export const JoinTypeEnum = {
        Inner: 'INNER' as JoinTypeEnum,
        Left: 'LEFT' as JoinTypeEnum,
        Right: 'RIGHT' as JoinTypeEnum
    };
}


