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
import { DataSourceLoadOptionsFoodJoinListValueParentFetchesInnerAttributeDeclaringTypeSingularAttributesInnerType } from './dataSourceLoadOptionsFoodJoinListValueParentFetchesInnerAttributeDeclaringTypeSingularAttributesInnerType';
import { DataSourceLoadOptionsFoodJoinListValueParentFetchesInnerAttributeDeclaringTypeAttributesInnerJavaMember } from './dataSourceLoadOptionsFoodJoinListValueParentFetchesInnerAttributeDeclaringTypeAttributesInnerJavaMember';


export interface DataSourceLoadOptionsFoodJoinListValueParentFetchesInnerAttributeDeclaringTypeSingularAttributesInner { 
    type?: DataSourceLoadOptionsFoodJoinListValueParentFetchesInnerAttributeDeclaringTypeSingularAttributesInnerType;
    optional?: boolean;
    id?: boolean;
    version?: boolean;
    name?: string;
    collection?: boolean;
    association?: boolean;
    javaMember?: DataSourceLoadOptionsFoodJoinListValueParentFetchesInnerAttributeDeclaringTypeAttributesInnerJavaMember;
    persistentAttributeType?: DataSourceLoadOptionsFoodJoinListValueParentFetchesInnerAttributeDeclaringTypeSingularAttributesInner.PersistentAttributeTypeEnum;
    bindableType?: DataSourceLoadOptionsFoodJoinListValueParentFetchesInnerAttributeDeclaringTypeSingularAttributesInner.BindableTypeEnum;
}
export namespace DataSourceLoadOptionsFoodJoinListValueParentFetchesInnerAttributeDeclaringTypeSingularAttributesInner {
    export type PersistentAttributeTypeEnum = 'MANY_TO_ONE' | 'ONE_TO_ONE' | 'BASIC' | 'EMBEDDED' | 'MANY_TO_MANY' | 'ONE_TO_MANY' | 'ELEMENT_COLLECTION';
    export const PersistentAttributeTypeEnum = {
        ManyToOne: 'MANY_TO_ONE' as PersistentAttributeTypeEnum,
        OneToOne: 'ONE_TO_ONE' as PersistentAttributeTypeEnum,
        Basic: 'BASIC' as PersistentAttributeTypeEnum,
        Embedded: 'EMBEDDED' as PersistentAttributeTypeEnum,
        ManyToMany: 'MANY_TO_MANY' as PersistentAttributeTypeEnum,
        OneToMany: 'ONE_TO_MANY' as PersistentAttributeTypeEnum,
        ElementCollection: 'ELEMENT_COLLECTION' as PersistentAttributeTypeEnum
    };
    export type BindableTypeEnum = 'SINGULAR_ATTRIBUTE' | 'PLURAL_ATTRIBUTE' | 'ENTITY_TYPE';
    export const BindableTypeEnum = {
        SingularAttribute: 'SINGULAR_ATTRIBUTE' as BindableTypeEnum,
        PluralAttribute: 'PLURAL_ATTRIBUTE' as BindableTypeEnum,
        EntityType: 'ENTITY_TYPE' as BindableTypeEnum
    };
}


