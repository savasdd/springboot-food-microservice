export * from './stockController.service';
import { StockControllerService } from './stockController.service';
export * from './stockProductController.service';
import { StockProductControllerService } from './stockProductController.service';
export const APIS = [StockControllerService, StockProductControllerService];
