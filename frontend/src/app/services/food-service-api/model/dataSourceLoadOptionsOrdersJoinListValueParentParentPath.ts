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
import { DataSourceLoadOptionsOrdersJoinListValueParentModel } from './dataSourceLoadOptionsOrdersJoinListValueParentModel';
import { DataSourceLoadOptionsOrdersJoinListValueParentParentPathCompoundSelectionItemsInner } from './dataSourceLoadOptionsOrdersJoinListValueParentParentPathCompoundSelectionItemsInner';


export interface DataSourceLoadOptionsOrdersJoinListValueParentParentPath { 
    model?: DataSourceLoadOptionsOrdersJoinListValueParentModel;
    compoundSelectionItems?: Array<DataSourceLoadOptionsOrdersJoinListValueParentParentPathCompoundSelectionItemsInner>;
    compoundSelection?: boolean;
    alias?: string;
}
