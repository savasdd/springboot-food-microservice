import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import CustomStore from "devextreme/data/custom_store";
import {faShoppingBasket, faTrashAlt} from "@fortawesome/free-solid-svg-icons";
import {DxDataGridComponent} from "devextreme-angular";
import {Orders} from "../../../services/food-service-api";
import {MessageService} from "../../../services/message.service";
import {Router} from "@angular/router";
import {UtilService} from "../../../services/util.service";
import {GenericService} from "../../../services/generic.service";

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
  foodService: GenericService;
  orderService: GenericService;
  basketList: Array<{ ID: string, Name: string, Price: number, Image: string }> = [];

  constructor(public service: GenericService,
              private messageService: MessageService,
              private router: Router,
              private cd: ChangeDetectorRef) {
    this.foodService = this.service.instance('foods');
    this.orderService = this.service.instance('foods/orders');
    this.loadGrid();
    this.loadOrderGrid();
    this.totalPrice = 0;
  }

  ngOnInit(): void {
  }

  loadGrid() {
    this.dataSource = new CustomStore({
      key: 'id',
      load: (loadOptions) => {
        return this.foodService.customPost('custom/orders', UtilService.setPage(loadOptions)).then((response: any) => {
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

    });
  }

  loadOrderGrid() {
    this.dataOrderSource = new CustomStore({
      key: 'id',
      load: (loadOptions) => {
        loadOptions.filter = [];
        loadOptions.filter.push(['status', '=', Orders.StatusEnum.Basket]);
        return this.orderService.findAll(UtilService.setPage(loadOptions)).then((response: any) => {
          this.calculateBasket(response.items);
          return {
            data: response.items,
            totalCount: response.totalCount
          };
        });
      },

      byKey: (key) => {
        return this.orderService.findOne(key).then((response) => {
          return response;
        }, err => {
          throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
        });
      },
      remove: (key) => {
        return this.orderService.delete(key).then((response) => {
            return;
          },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      }

    });
  }

  addBasket(event: any) {
    const data = {
      food: {id: event.id},
      price: event.price,
      totalPrice: 0,
      status: Orders.StatusEnum.Basket
    };

    this.orderService.save(data).then((response) => {
      if (response) {
        this.messageService.success(event.foodName + " Sepete Eklendi");
        this.refreshDataGrid();
      }
    });
    //this.cd.detectChanges();
  }

  callBasket() {
    if (this.totalPrice > 0) {
      this.router.navigate(['home/foods/baskets']);
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






