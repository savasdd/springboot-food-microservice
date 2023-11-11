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


/**
 * 
 */
export interface Category { 
    id?: number;
    name?: string;
    description?: string;
    categoryType?: Category.CategoryTypeEnum;
}
export namespace Category {
    export type CategoryTypeEnum = 'SEBZE' | 'MEYVE' | 'KAHVALTI' | 'FIRIN' | 'ICECEK' | 'ATISTIRMA';
    export const CategoryTypeEnum = {
        Sebze: 'SEBZE' as CategoryTypeEnum,
        Meyve: 'MEYVE' as CategoryTypeEnum,
        Kahvalti: 'KAHVALTI' as CategoryTypeEnum,
        Firin: 'FIRIN' as CategoryTypeEnum,
        Icecek: 'ICECEK' as CategoryTypeEnum,
        Atistirma: 'ATISTIRMA' as CategoryTypeEnum
    };
}


