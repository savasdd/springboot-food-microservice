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
import { DataSourceLoadOptionsDepartmentJoinListValueParentFetchesInnerAttributeDeclaringTypeAttributesInnerJavaMember } from './dataSourceLoadOptionsDepartmentJoinListValueParentFetchesInnerAttributeDeclaringTypeAttributesInnerJavaMember';
import { DataSourceLoadOptionsDepartmentJoinListValueParentFetchesInnerAttributeDeclaringType } from './dataSourceLoadOptionsDepartmentJoinListValueParentFetchesInnerAttributeDeclaringType';


export interface DataSourceLoadOptionsDepartmentJoinListValueParentFetchesInnerAttribute { 
    name?: string;
    collection?: boolean;
    declaringType?: DataSourceLoadOptionsDepartmentJoinListValueParentFetchesInnerAttributeDeclaringType;
    association?: boolean;
    javaMember?: DataSourceLoadOptionsDepartmentJoinListValueParentFetchesInnerAttributeDeclaringTypeAttributesInnerJavaMember;
    persistentAttributeType?: DataSourceLoadOptionsDepartmentJoinListValueParentFetchesInnerAttribute.PersistentAttributeTypeEnum;
}
export namespace DataSourceLoadOptionsDepartmentJoinListValueParentFetchesInnerAttribute {
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
}

