export * from './userAuthController.service';
import {UserAuthController} from "./userAuthController.service";

export * from './authController.service';
import {AuthControllerService} from './authController.service';

export const APIS = [AuthControllerService, UserAuthController];
