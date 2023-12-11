import { Component, OnInit, ViewChild } from '@angular/core';
import { DxDataGridComponent } from "devextreme-angular";
import { Stock } from "../../../services/stock-service-api";
import CustomStore from "devextreme/data/custom_store";
import { UtilService } from "../../../services/util.service";
import { GenericService } from "../../../services/generic.service";
import StatusEnum = Stock.StatusEnum;
import UnitEnum = Stock.UnitEnum;


@Component({
  selector: 'app-stock',
  templateUrl: './stock.component.html',
  styleUrls: ['./stock.component.scss']
})
export class StockComponent implements OnInit {
  dataSource: any = {};
  foodDataSource: any = {};
  @ViewChild('stockDataGrid', { static: true }) stockDataGrid: any = DxDataGridComponent;
  dropDownOptions: any;
  stockService: GenericService;
  dataTypeSource: any = [
    { name: StatusEnum.New },
    { name: StatusEnum.Accept },
    { name: StatusEnum.Reject },
    { name: StatusEnum.Confirmed },
    { name: StatusEnum.Rollback },
  ];
  dataUnitSource: any = [
    { name: UnitEnum.Adet },
    { name: UnitEnum.Kilogram },
    { name: UnitEnum.Gram },
    { name: UnitEnum.Ton },
    { name: UnitEnum.Litre },
    { name: UnitEnum.Metre },
    { name: UnitEnum.Santimetre },
  ];

  constructor(public service: GenericService) {
    this.stockService = this.service.instance('stocks');
    this.loadGrid();
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
