import { Component, OnInit, ViewChild } from '@angular/core';
import { DxDataGridComponent } from "devextreme-angular";
import CustomStore from "devextreme/data/custom_store";
import { Food, OrderEvent } from "../../../services/food-service-api";
import { UtilService } from "../../../services/util.service";
import { GenericService } from "../../../services/generic.service";
import StatusEnum = OrderEvent.StatusEnum;
import ClassTypeEnum = Food.ClassTypeEnum;

@Component({
  selector: 'app-food',
  templateUrl: './food.component.html',
  styleUrls: ['./food.component.scss']
})
export class FoodComponent implements OnInit {
  @ViewChild('foodDataGrid', { static: true }) foodDataGrid: any = DxDataGridComponent;
  dataSource: any = {};
  categoryDataSource: any = {};
  restaurantDataSource: any = {};

  readonly allowedPageSizes = [5, 10, 'all'];
  displayMode = 'full';
  showPageSizeSelector = true;
  showInfo = true;
  showNavButtons = true;
  foodData: any;
  foodService: GenericService;
  categoryService: GenericService;
  restaurantService: GenericService;
  dataTypeSource: any = [
    { name: StatusEnum.New },
    { name: StatusEnum.Accept },
    { name: StatusEnum.Reject },
    { name: StatusEnum.Confirmed },
    { name: StatusEnum.Rollback },
  ];
  classTypeSource: any = [
    { name: ClassTypeEnum.Diamond },
    { name: ClassTypeEnum.Platinum },
    { name: ClassTypeEnum.Gold },
  ];

  constructor(public service: GenericService) {
    this.categoryService = this.service.instance('foods/categorys');
    this.restaurantService = this.service.instance('foods/restaurants');
    this.foodService = this.service.instance('foods');

    this.loadCategory();
    this.loadRestaurant();
    this.loadGrid();

  }

  ngOnInit(): void {
  }

  refreshDataGrid(e: any) {
    this.foodDataGrid.instance.refresh();
  }

  onEditorPreparing(e: any) {
    if (e.parentType === 'dataRow') {
      this.foodData = e.row.data;
    }
  }


  loadCategory() {
    this.categoryService.findAll(UtilService.setPage({ "skip": 0, "take": 100 })).then((response: any) => {
      this.categoryDataSource = response.items;
    });
  }

  loadRestaurant() {
    this.restaurantService.findAll(UtilService.setPage({ "skip": 0, "take": 100 })).then((response: any) => {
      this.restaurantDataSource = response.items;
    });
  }

  loadGrid() {
    this.dataSource = new CustomStore({
      key: 'id',
      load: (loadOptions) => {
        return this.foodService.findAll(UtilService.setPage(loadOptions)).then((response: any) => {
          return {
            data: response.items,
            totalCount: response.totalCount
          };
        });
      },

      byKey: (key) => {
        return this.foodService.findOne(key).then((response) => {
          return response;
        }, err => {
          throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
        });
      },

      insert: (values) => {
        return this.foodService.save(values).then((response) => {
          return;
        },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      },
      update: (key, values: any) => {
        values.id = key;
        return this.foodService.update(key, values).then((response) => {
          return;
        },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      },
      remove: (key) => {
        return this.foodService.delete(key).then((response) => {
          return;
        },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      }
    });
  }

}
