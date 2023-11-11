import {Component, OnInit, ViewChild} from '@angular/core';
import {StockService} from "../../../services/stock.service";
import {DxDataGridComponent} from "devextreme-angular";
import {Stock} from "../../../services/stock-service-api";
import CustomStore from "devextreme/data/custom_store";
import {FoodService} from "../../../services/food.service";
import {Food} from "../../../services/food-service-api";
import StatusEnum = Stock.StatusEnum;
import {UtilService} from "../../../services/util.service";

@Component({
  selector: 'app-stock',
  templateUrl: './stock.component.html',
  styleUrls: ['./stock.component.scss']
})
export class StockComponent implements OnInit {
  dataSource: any = {};
  foodDataSource: any = {};
  @ViewChild('stockDataGrid', {static: true}) stockDataGrid: any = DxDataGridComponent;
  dropDownOptions: any;
  dataTypeSource: any = [
    {name: StatusEnum.New},
    {name: StatusEnum.Accept},
    {name: StatusEnum.Reject},
    {name: StatusEnum.Confirmed},
    {name: StatusEnum.Rollback},
  ];

  constructor(private service: StockService, private foodService: FoodService) {
    this.loadGrid();
    this.loadFood();
  }

  ngOnInit(): void {
  }

  onSelectionPersonChanged(selectedRowKeys: any, cellInfo: any, dropDownBoxComponent: any) {
    cellInfo.setValue(selectedRowKeys[0]);
    if (selectedRowKeys.length > 0) {
      dropDownBoxComponent.close();
    }
  }

  refreshDataGrid(e: any) {
    this.stockDataGrid.instance.refresh();
  }

  loadFood() {
    this.foodService.findAlls().subscribe((response: Food[]) => {
      this.foodDataSource = response;
    });

    this.foodDataSource = new CustomStore({
      load: (loadOptions) => {
        return this.foodService.findAll(UtilService.setPage(loadOptions)).toPromise().then((response: any) => {
          return {
            data: response.items,
            totalCount: response.totalCount
          };
        });
      },

      byKey: (key) => {
        return this.foodService.findOne(key).toPromise().then((response) => {
          return response;
        }, err => {
          throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
        });
      },
    });
  }

  loadGrid() {
    this.dataSource = new CustomStore({
      load: (loadOptions) => {
        return this.service.findAll(UtilService.setPage(loadOptions)).toPromise().then((response: any) => {
          return {
            data: response.items,
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
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      },
      remove: (key) => {
        return this.service.delete(key).toPromise().then((response) => {
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
