import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {FoodService} from "../../../services/food.service";
import CustomStore from "devextreme/data/custom_store";
import {faShoppingBasket, faTrashAlt} from "@fortawesome/free-solid-svg-icons";
import {OrderService} from "../../../services/order.service";
import {DxDataGridComponent} from "devextreme-angular";
import {Orders} from "../../../services/food-service-api";
import {StockService} from "../../../services/stock.service";
import {MessageService} from "../../../services/message.service";
import {Router} from "@angular/router";
import {UtilService} from "../../../services/util.service";

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss'],
  preserveWhitespaces: true,
})
export class OrderComponent implements OnInit {
  @ViewChild('orderDataGrid', {static: true}) orderDataGrid: any = DxDataGridComponent;
  dataSource: any = {};
  dataOrderSource: any = {};
  totalPrice: number = 0;
  basketList: Array<{ ID: string, Name: string, Price: number, Image: string }> = [];

  constructor(private service: FoodService,
              private orderService: OrderService,
              private stockService: StockService,
              private messageService: MessageService,
              private router: Router,
              private cd: ChangeDetectorRef) {
    this.loadGrid();
    this.loadOrderGrid();
    this.totalPrice = 0;
  }

  ngOnInit(): void {
  }

  loadGrid() {
    this.dataSource = new CustomStore({
      // key: 'id',
      load: (loadOptions) => {
        return this.service.findAllOrder(UtilService.setPage(loadOptions)).toPromise().then((response: any) => {
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

    });
  }

  loadOrderGrid() {
    this.dataOrderSource = new CustomStore({
      load: (loadOptions) => {
        loadOptions.filter = [];
        loadOptions.filter.push(['status', '=', Orders.StatusEnum.Basket]);
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
      remove: (key) => {
        return this.orderService.delete(key).toPromise().then((response) => {
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

  addBasket(event: any) {
    const data = {
      food: {foodId: event.foodId},
      price: event.price,
      totalPrice: 0,
      status: Orders.StatusEnum.Basket
    };

    this.orderService.save(data).subscribe((response) => {
      if (response) {
        this.messageService.success(event.foodName + " Sepete Eklendi");
        this.refreshDataGrid();
      }
    });
    //this.cd.detectChanges();
  }

  callBasket() {
    if (this.totalPrice > 0) {
      this.router.navigate(['/foods/baskets']);
    }
  }

  calculateBasket(data: any[]) {
    if (data) {
      this.totalPrice = 0;
      data.map((m) => {
        this.totalPrice = this.totalPrice + m.price;
      });
    }
  }


  refreshDataGrid() {
    this.orderDataGrid.instance.refresh();
  }

  protected readonly faShoppingBasket = faShoppingBasket;
  protected readonly faTrashAlt = faTrashAlt;
}






