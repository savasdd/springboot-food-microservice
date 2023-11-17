import {CategoryControllerService} from "./categoryController.service";

export * from './authController.service';
import { AuthControllerService } from './authController.service';
export * from './groupController.service';
import { GroupControllerService } from './groupController.service';
export * from './rolController.service';
import { RolControllerService } from './rolController.service';
export * from './userController.service';
import { UserControllerService } from './userController.service';
export const APIS = [AuthControllerService, GroupControllerService, RolControllerService, UserControllerService,CategoryControllerService];
