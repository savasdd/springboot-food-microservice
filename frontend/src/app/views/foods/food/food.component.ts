import {Component, OnInit, ViewChild} from '@angular/core';
import {DxDataGridComponent} from "devextreme-angular";
import {FoodService} from "../../../services/food.service";
import CustomStore from "devextreme/data/custom_store";
import {CategoryService} from "../../../services/category.service";
import {Category, OrderEvent} from "../../../services/food-service-api";
import StatusEnum = OrderEvent.StatusEnum;

@Component({
  selector: 'app-food',
  templateUrl: './food.component.html',
  styleUrls: ['./food.component.scss']
})
export class FoodComponent implements OnInit {
  dataSource: any = {};
  categoryDataSource: any = {};
  @ViewChild('foodDataGrid', {static: true}) foodDataGrid: any = DxDataGridComponent;

  readonly allowedPageSizes = [5, 10, 'all'];
  displayMode = 'full';
  showPageSizeSelector = true;
  showInfo = true;
  showNavButtons = true;
  foodData: any;
  dataTypeSource: any = [
    {name: StatusEnum.New},
    {name: StatusEnum.Accept},
    {name: StatusEnum.Reject},
    {name: StatusEnum.Confirmed},
    {name: StatusEnum.Rollback},
  ];

  constructor(private service: FoodService, private categoryService: CategoryService) {
    this.loadGrid();

    this.categoryService.findAlls().subscribe((response: Category[]) => {
      this.categoryDataSource = response;
    });
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


  loadGrid() {
    this.dataSource = new CustomStore({
      key: 'foodId',
      load: (loadOptions) => {
        // loadOptions.filter = [];
        // loadOptions.filter.push(['searchImage', '=', false]);
        return this.service.findAll(loadOptions).toPromise().then((response: any) => {
          return {
            data: response.data,
            totalCount: response.totalCount
          };
        });
      },

      byKey: (key) => {
        return this.service.findOne(key).toPromise().then((response) => {
          return response;
        }, err => {
          throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
        });
      },

      insert: (values) => {
        return this.service.save(values).toPromise().then((response) => {
            return;
          },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      },
      update: (key, values: any) => {
        values.id = key;
        return this.service.update(key, values).toPromise().then((response) => {
            return;
          },
          err => {
            const message = 'Kayıt Güncelleme Hatası: ' + err.error.errorMessage;
            console.log(message);
          }
        );
      },
      remove: (key) => {
        return this.service.delete(key).toPromise().then((response) => {
            return;
          },
          err => {
            const message = 'Kayıt Silme Hatası: ' + err.error.errorMessage;
            console.log(message);
          }
        );
      }
    });
  }

}
