import {Component, OnInit, ViewChild} from '@angular/core';
import {PaymentService} from "../../../services/payment.service";
import {DxDataGridComponent} from "devextreme-angular";
import CustomStore from "devextreme/data/custom_store";
import {Payment} from "../../../services/payment-service-api";
import {StockService} from "../../../services/stock.service";
import {Stock} from "../../../services/stock-service-api";
import StatusEnum = Payment.StatusEnum;
import {UtilService} from "../../../services/util.service";

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.scss']
})
export class PaymentComponent implements OnInit {
  dataSource: any = {};
  stockDataSource: any = {};
  @ViewChild('paymentDataGrid', {static: true}) paymentDataGrid: any = DxDataGridComponent;
  dataTypeSource: any = [
    {name: StatusEnum.New},
    {name: StatusEnum.Accept},
    {name: StatusEnum.Reject},
    {name: StatusEnum.Confirmed},
    {name: StatusEnum.Rollback},
  ];

  constructor(private service: PaymentService, private stockService: StockService) {
    this.loadGrid();
    this.loadStock();
  }

  ngOnInit(): void {
  }

  loadStock() {
    this.stockService.findAlls().subscribe((response: Stock[]) => {
      this.stockDataSource = response;
    });
  }

  refreshDataGrid(e: any) {
    this.paymentDataGrid.instance.refresh();
  }

  loadGrid() {
    this.dataSource = new CustomStore({
      key: 'id',
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
