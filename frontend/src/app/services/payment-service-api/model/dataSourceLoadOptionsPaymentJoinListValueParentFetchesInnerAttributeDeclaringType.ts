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
import { DataSourceLoadOptionsPaymentJoinListValueParentFetchesInnerAttributeDeclaringTypeAttributesInner } from './dataSourceLoadOptionsPaymentJoinListValueParentFetchesInnerAttributeDeclaringTypeAttributesInner';
import { DataSourceLoadOptionsPaymentJoinListValueParentFetchesInnerAttributeDeclaringTypeSingularAttributesInner } from './dataSourceLoadOptionsPaymentJoinListValueParentFetchesInnerAttributeDeclaringTypeSingularAttributesInner';
import { DataSourceLoadOptionsPaymentJoinListValueParentFetchesInnerAttributeDeclaringTypeDeclaredPluralAttributesInner } from './dataSourceLoadOptionsPaymentJoinListValueParentFetchesInnerAttributeDeclaringTypeDeclaredPluralAttributesInner';


export interface DataSourceLoadOptionsPaymentJoinListValueParentFetchesInnerAttributeDeclaringType { 
    attributes?: Set<DataSourceLoadOptionsPaymentJoinListValueParentFetchesInnerAttributeDeclaringTypeAttributesInner>;
    declaredPluralAttributes?: Set<DataSourceLoadOptionsPaymentJoinListValueParentFetchesInnerAttributeDeclaringTypeDeclaredPluralAttributesInner>;
    pluralAttributes?: Set<DataSourceLoadOptionsPaymentJoinListValueParentFetchesInnerAttributeDeclaringTypeDeclaredPluralAttributesInner>;
    singularAttributes?: Set<DataSourceLoadOptionsPaymentJoinListValueParentFetchesInnerAttributeDeclaringTypeSingularAttributesInner>;
    declaredSingularAttributes?: Set<DataSourceLoadOptionsPaymentJoinListValueParentFetchesInnerAttributeDeclaringTypeSingularAttributesInner>;
    declaredAttributes?: Set<DataSourceLoadOptionsPaymentJoinListValueParentFetchesInnerAttributeDeclaringTypeAttributesInner>;
    persistenceType?: DataSourceLoadOptionsPaymentJoinListValueParentFetchesInnerAttributeDeclaringType.PersistenceTypeEnum;
}
export namespace DataSourceLoadOptionsPaymentJoinListValueParentFetchesInnerAttributeDeclaringType {
    export type PersistenceTypeEnum = 'ENTITY' | 'EMBEDDABLE' | 'MAPPED_SUPERCLASS' | 'BASIC';
    export const PersistenceTypeEnum = {
        Entity: 'ENTITY' as PersistenceTypeEnum,
        Embeddable: 'EMBEDDABLE' as PersistenceTypeEnum,
        MappedSuperclass: 'MAPPED_SUPERCLASS' as PersistenceTypeEnum,
        Basic: 'BASIC' as PersistenceTypeEnum
    };
}


