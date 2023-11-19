import {Component, OnInit, ViewChild} from '@angular/core';
import {DxDataGridComponent} from "devextreme-angular";
import CustomStore from "devextreme/data/custom_store";
import {Payment} from "../../../services/payment-service-api";
import {UtilService} from "../../../services/util.service";
import {GenericService} from "../../../services/generic.service";
import StatusEnum = Payment.StatusEnum;

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.scss']
})
export class PaymentComponent implements OnInit {
  dataSource: any = {};
  stockDataSource: any = {};
  @ViewChild('paymentDataGrid', {static: true}) paymentDataGrid: any = DxDataGridComponent;
  stockService: GenericService;
  paymentService: GenericService;
  dataTypeSource: any = [
    {name: StatusEnum.New},
    {name: StatusEnum.Accept},
    {name: StatusEnum.Reject},
    {name: StatusEnum.Confirmed},
    {name: StatusEnum.Rollback},
  ];

  constructor(private service: GenericService) {
    this.stockService = this.service.instance('stocks');
    this.paymentService = this.service.instance('payments');
    this.loadStock();
    this.loadGrid();
  }

  ngOnInit(): void {
  }

  loadStock() {
    this.stockService.findAll({skip: 0, take: 200}).then((response: any) => {
      this.stockDataSource = response.items;
    });
  }

  refreshDataGrid(e: any) {
    this.paymentDataGrid.instance.refresh();
  }

  loadGrid() {
    this.dataSource = new CustomStore({
      key: 'id',
      load: (loadOptions) => {
        return this.paymentService.findAll(UtilService.setPage(loadOptions)).then((response: any) => {
          return {
            data: response.items,
            totalCount: response.totalCount
          };
        });
      },

      byKey: (key) => {
        return this.paymentService.findOne(key).then((response) => {
          return response;
        });
      },

      insert: (values) => {
        return this.paymentService.save(values).then((response) => {
            return;
          }
        );
      },
      update: (key, values: any) => {
        values.id = key;
        return this.paymentService.update(key, values).then((response) => {
            return;
          }
        );
      },
      remove: (key) => {
        return this.paymentService.delete(key).then((response) => {
            return;
          }
        );
      }
    });
  }

}
