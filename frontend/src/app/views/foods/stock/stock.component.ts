import {Component, OnInit, ViewChild} from '@angular/core';
import {DxDataGridComponent} from "devextreme-angular";
import {Stock} from "../../../services/stock-service-api";
import CustomStore from "devextreme/data/custom_store";
import {UtilService} from "../../../services/util.service";
import {GenericService} from "../../../services/generic.service";
import StatusEnum = Stock.StatusEnum;

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
  stockService: GenericService;
  foodService: GenericService;
  dataTypeSource: any = [
    {name: StatusEnum.New},
    {name: StatusEnum.Accept},
    {name: StatusEnum.Reject},
    {name: StatusEnum.Confirmed},
    {name: StatusEnum.Rollback},
  ];

  constructor(public service: GenericService) {
    this.stockService = this.service.instance('stocks');
    this.foodService = this.service.instance('foods');
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
    this.foodService.findAll({"skip": 0, "take": 200}).then((response: any) => {
      this.foodDataSource = response.items;
    });
  }

  loadGrid() {
    this.dataSource = new CustomStore({
      key: 'id',
      load: (loadOptions) => {
        return this.stockService.findAll(UtilService.setPage(loadOptions)).then((response: any) => {
          return {
            data: response.items,
            totalCount: response.totalCount
          };
        });
      },

      byKey: (key) => {
        return this.stockService.findOne(key).then((response) => {
          return response;
        }, err => {
          throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
        });
      },

      insert: (values) => {
        return this.stockService.save(values).then((response) => {
            return;
          },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      },
      update: (key, values: any) => {
        values.id = key;
        return this.stockService.update(key, values).then((response) => {
            return;
          },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      },
      remove: (key) => {
        return this.stockService.delete(key).then((response) => {
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
