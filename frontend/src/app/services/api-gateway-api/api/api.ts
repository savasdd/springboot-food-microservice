import {UserController} from "./userController.service";

export * from './authController.service';
import {AuthControllerService} from './authController.service';

export const APIS = [AuthControllerService, UserController];
