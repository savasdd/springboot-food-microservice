export * from './departmentController.service';
import { DepartmentControllerService } from './departmentController.service';
export * from './userDepartmentController.service';
import { UserDepartmentControllerService } from './userDepartmentController.service';
export const APIS = [DepartmentControllerService, UserDepartmentControllerService];
