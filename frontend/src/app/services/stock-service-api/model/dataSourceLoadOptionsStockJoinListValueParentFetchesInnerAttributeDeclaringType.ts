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
import { DataSourceLoadOptionsStockJoinListValueParentFetchesInnerAttributeDeclaringTypeDeclaredSingularAttributesInner } from './dataSourceLoadOptionsStockJoinListValueParentFetchesInnerAttributeDeclaringTypeDeclaredSingularAttributesInner';
import { DataSourceLoadOptionsStockJoinListValueParentFetchesInnerAttributeDeclaringTypeDeclaredPluralAttributesInner } from './dataSourceLoadOptionsStockJoinListValueParentFetchesInnerAttributeDeclaringTypeDeclaredPluralAttributesInner';
import { DataSourceLoadOptionsStockJoinListValueParentFetchesInnerAttributeDeclaringTypeAttributesInner } from './dataSourceLoadOptionsStockJoinListValueParentFetchesInnerAttributeDeclaringTypeAttributesInner';


export interface DataSourceLoadOptionsStockJoinListValueParentFetchesInnerAttributeDeclaringType { 
    attributes?: Set<DataSourceLoadOptionsStockJoinListValueParentFetchesInnerAttributeDeclaringTypeAttributesInner>;
    declaredPluralAttributes?: Set<DataSourceLoadOptionsStockJoinListValueParentFetchesInnerAttributeDeclaringTypeDeclaredPluralAttributesInner>;
    declaredSingularAttributes?: Set<DataSourceLoadOptionsStockJoinListValueParentFetchesInnerAttributeDeclaringTypeDeclaredSingularAttributesInner>;
    declaredAttributes?: Set<DataSourceLoadOptionsStockJoinListValueParentFetchesInnerAttributeDeclaringTypeAttributesInner>;
    pluralAttributes?: Set<DataSourceLoadOptionsStockJoinListValueParentFetchesInnerAttributeDeclaringTypeDeclaredPluralAttributesInner>;
    singularAttributes?: Set<DataSourceLoadOptionsStockJoinListValueParentFetchesInnerAttributeDeclaringTypeDeclaredSingularAttributesInner>;
    persistenceType?: DataSourceLoadOptionsStockJoinListValueParentFetchesInnerAttributeDeclaringType.PersistenceTypeEnum;
}
export namespace DataSourceLoadOptionsStockJoinListValueParentFetchesInnerAttributeDeclaringType {
    export type PersistenceTypeEnum = 'ENTITY' | 'EMBEDDABLE' | 'MAPPED_SUPERCLASS' | 'BASIC';
    export const PersistenceTypeEnum = {
        Entity: 'ENTITY' as PersistenceTypeEnum,
        Embeddable: 'EMBEDDABLE' as PersistenceTypeEnum,
        MappedSuperclass: 'MAPPED_SUPERCLASS' as PersistenceTypeEnum,
        Basic: 'BASIC' as PersistenceTypeEnum
    };
}

