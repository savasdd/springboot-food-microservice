import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {FoodService} from "../../../services/food.service";
import CustomStore from "devextreme/data/custom_store";
import {faBasketShopping, faCoffee, faShoppingBasket} from "@fortawesome/free-solid-svg-icons";
import DataSource from "devextreme/data/data_source";

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss'],
  preserveWhitespaces: true,
})
export class OrderComponent implements OnInit {
  dataSource: any = {};
  dataSourceBasket: any;
  totalPrice: number = 0;
  foodData: any;
  basketList: Array<{ ID: string, Name: string, Price: number, Image: string }> = [];

  constructor(private service: FoodService, private cd: ChangeDetectorRef) {
    this.loadGrid();

    this.totalPrice = 0;
    this.basketList = [];
    this.dataSourceBasket = new DataSource({
      store: this.basketList,
    });
  }

  ngOnInit(): void {
  }

  loadGrid() {
    this.dataSource = new CustomStore({
      key: 'foodId',
      load: (loadOptions) => {
        return this.service.findAllOrder(loadOptions).toPromise().then((response: any) => {
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

  addBasket(event: any) {
    this.basketList.push({ID: event.foodId, Name: event.foodName, Price: event.price, Image: event.image});
    //this.cd.detectChanges();
    this.dataSourceBasket.load();
    this.calculateBasket();
  }

  calculateBasket() {
    if (this.basketList.length > 0) {
      this.basketList.map((m) => {
        this.totalPrice = this.totalPrice + m.Price;
      });
    }

  }


  pushData(dto: any): any {
    const dataList: never[] = [];
    let array: never[] = [];
    dto.map((m: any) => {
      const list = this.pushDataList(m.foodId, m.foodName, 'Diamond', null, m.price, m.description, dataList);
      array = list.length > 0 ? list : [];
    });

    this.foodData = array.length > 0 ? array : [];
    return array.length > 0 ? array : [];
  }


  pushDataList(Id: any, Name: any, Class: any, Images: any, Price: any, Notes: any, dataList: any): any {
    dataList.push({
      ID: Id,
      Name: Name,
      Class: Class,
      Images: Images,
      Price: Price,
      Notes: Notes,
    });
    return dataList;
  }

  protected readonly faCoffee = faCoffee;
  protected readonly faBasketShopping = faBasketShopping;
  protected readonly faShoppingBasket = faShoppingBasket;
}


export class FoodData {
  ID: number | undefined;
  Name: string | undefined;
  Picture: string | undefined;
  Notes: string | undefined;
  Class: string | undefined;
  Price: number | undefined;
}



