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
import { LoadResultGroup } from './loadResultGroup';
import { Food } from './food';


/**
 * 
 */
export interface LoadResultFood { 
    data?: Array<Food>;
    totalCount?: number;
    groupData?: Array<LoadResultGroup>;
    groupCount?: number;
    success?: boolean;
    errorId?: number;
    result?: Array<Food>;
}

