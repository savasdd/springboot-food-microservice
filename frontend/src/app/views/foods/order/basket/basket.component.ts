import {ChangeDetectorRef, Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import DataSource from 'devextreme/data/data_source';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.scss']
})
export class BasketComponent implements OnChanges {
  @Input() basketList: any;
  dataSource: any;
  totalPrice: number = 0;
  products: any[] = [{
    ID: 1,
    Name: 'Muz',
    Price: 25,
    Image: 'assets/images/food/muz.png',
  }, {
    ID: 3,
    Name: 'Elma',
    Price: 14,
    Image: 'assets/images/food/elma.png',
  }, {
    ID: 4,
    Name: 'Pizza',
    Price: 78,
    Image: 'assets/images/food/pizza.png',
  }];

  constructor(private cd: ChangeDetectorRef) {
    this.dataSource = new DataSource({
      store: this.products,
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log(this.basketList)
  }

}
