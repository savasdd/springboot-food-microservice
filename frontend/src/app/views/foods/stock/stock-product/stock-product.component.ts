import { Component, Input, OnChanges, SimpleChanges, ViewChild } from '@angular/core';
import { DxDataGridComponent } from "devextreme-angular";
import CustomStore from "devextreme/data/custom_store";
import { GenericService } from "../../../../services/generic.service";
import { UtilService } from "../../../../services/util.service";

@Component({
  selector: 'app-stock-product',
  templateUrl: './stock-product.component.html',
  styleUrls: ['./stock-product.component.scss']
})
export class StockProductComponent implements OnChanges {
  @Input() data: any;
  dataSource: any = {};
  foodDataSource: any = {};
  @ViewChild('dataSourceGrid', { static: true }) dataSourceGrid: any = DxDataGridComponent;
  events: Array<string> = [];
  stockId: any = null;
  productService: GenericService;
  foodService: GenericService;

  constructor(private service: GenericService) {
    this.loadGrid = this.loadGrid.bind(this);
    this.loadFood = this.loadFood.bind(this);
    this.productService = this.service.instance('stocks/products');
    this.foodService = this.service.instance('foods');

  }

  ngOnChanges(changes: SimpleChanges): void {
    this.stockId = this.data ? this.data.id : null;
    if (this.stockId !== null) {
      this.loadGrid();
      this.loadFood();
    }
  }


  logEvent(eventName: any) {
    this.events.unshift(eventName);
  }

  refreshDataGrid(e: any) {
    this.dataSourceGrid.instance.refresh();
  }

  loadFood() {
    this.foodService.findAll({ "skip": 0, "take": 500 }).then((response: any) => {
      this.foodDataSource = response.items;
    });
  }

  loadGrid() {
    this.dataSource = new CustomStore({
      key: 'id',
      load: (loadOptions) => {
        loadOptions.filter = [];
        loadOptions.filter.push(['stock.id', '=', this.stockId]);
        return this.productService.findAll(UtilService.setPage(loadOptions)).then((response: any) => {
          return {
            data: response.items,
            totalCount: response.totalCount
          };
        });
      },

      byKey: (key) => {
        return this.productService.findOne(key).then((response) => {
          return response;
        }, err => {
          throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
        });
      },

      insert: (values) => {
        values.stock = { id: this.stockId };
        return this.productService.save(values).then((response) => {
          return;
        },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      },
      update: (key, values: any) => {
        values.id = key;
        values.stock = { id: this.stockId };
        return this.productService.update(key, values).then((response) => {
          return;
        },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      },
      remove: (key) => {
        return this.productService.delete(key).then((response) => {
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
