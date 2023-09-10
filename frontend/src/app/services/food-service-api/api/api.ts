export * from './categoryController.service';
import { CategoryControllerService } from './categoryController.service';
export * from './foodController.service';
import { FoodControllerService } from './foodController.service';
export * from './userController.service';
import { UserControllerService } from './userController.service';
export const APIS = [CategoryControllerService, FoodControllerService, UserControllerService];
