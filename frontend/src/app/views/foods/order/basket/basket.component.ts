import {ChangeDetectorRef, Component, OnChanges, SimpleChanges, ViewChild} from '@angular/core';
import CustomStore from "devextreme/data/custom_store";
import {OrderService} from "../../../../services/order.service";
import {DxDataGridComponent} from "devextreme-angular";
import {Orders} from "../../../../services/food-service-api";

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.scss']
})
export class BasketComponent implements OnChanges {
  @ViewChild('orderDataGrid', {static: true}) orderDataGrid: any = DxDataGridComponent;
  dataSource: any = {};
  totalPrice: number = 0;

  constructor(private cd: ChangeDetectorRef,
              private orderService: OrderService) {
    this.loadOrderGrid();

  }

  ngOnChanges(changes: SimpleChanges): void {
  }

  loadOrderGrid() {
    this.dataSource = new CustomStore({
      key: 'orderId',
      load: (loadOptions) => {
        // loadOptions.filter = [];
        // loadOptions.filter.push(['status', '=', Orders.StatusEnum.Basket]);
        return this.orderService.findAll(loadOptions).toPromise().then((response: any) => {
          this.calculateBasket(response.data);
          return {
            data: response.data,
            totalCount: response.totalCount
          };
        });
      },

      byKey: (key) => {
        return this.orderService.findOne(key).toPromise().then((response) => {
          return response;
        }, err => {
          throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
        });
      },

    });
  }

  paymentBasket() {
    if (this.totalPrice > 0) {

      this.refreshDataGrid();
    }
  }

  calculateBasket(data: any[]) {
    if (data) {
      this.totalPrice = 0;
      data.map((m) => {
        this.totalPrice = m.status === Orders.StatusEnum.Basket ? this.totalPrice + m.price : 0;
      });
    }
  }

  refreshDataGrid() {
    this.orderDataGrid.instance.refresh();
  }

}
