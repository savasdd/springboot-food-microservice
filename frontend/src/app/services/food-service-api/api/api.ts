export * from './categoryController.service';
import { CategoryControllerService } from './categoryController.service';
export * from './foodController.service';
import { FoodControllerService } from './foodController.service';
export * from './foodFileController.service';
import { FoodFileControllerService } from './foodFileController.service';
export * from './userFileController.service';
import { UserFileControllerService } from './userFileController.service';
export const APIS = [CategoryControllerService, FoodControllerService, FoodFileControllerService, UserFileControllerService];
