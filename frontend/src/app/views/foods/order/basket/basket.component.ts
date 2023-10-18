import {Component, OnChanges, SimpleChanges} from '@angular/core';
import DataSource from 'devextreme/data/data_source';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.scss']
})
export class BasketComponent implements OnChanges {
  dataSource: any;
  products: any[] = [{
    ID: 1,
    Name: 'Muz',
    Price: 25,
    ImageSrc: 'assets/images/food/muz.png',
  }, {
    ID: 3,
    Name: 'Elma',
    Price: 14,
    ImageSrc: 'assets/images/food/elma.png',
  }, {
    ID: 4,
    Name: 'Pizza',
    Price: 78,
    ImageSrc: 'assets/images/food/pizza.png',
  }];

  constructor() {
    this.dataSource = new DataSource({
      store: this.products,
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
  }

}
