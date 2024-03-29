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
export interface OrderEvent { 
    id?: string;
    foodId?: string;
    stockId?: string;
    paymentId?: string;
    stockCount?: number;
    amount?: number;
    status?: OrderEvent.StatusEnum;
    foodName?: string;
    source?: string;
    message?: string;
}
export namespace OrderEvent {
    export type StatusEnum = 'NEW' | 'ACCEPT' | 'REJECT' | 'CONFIRMED' | 'ROLLBACK';
    export const StatusEnum = {
        New: 'NEW' as StatusEnum,
        Accept: 'ACCEPT' as StatusEnum,
        Reject: 'REJECT' as StatusEnum,
        Confirmed: 'CONFIRMED' as StatusEnum,
        Rollback: 'ROLLBACK' as StatusEnum
    };
}


