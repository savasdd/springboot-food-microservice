import {Component, OnInit} from '@angular/core';
import ArrayStore from 'devextreme/data/array_store';
import DataSource from 'devextreme/data/data_source';
import {FoodService} from "../../../services/food.service";
import CustomStore from "devextreme/data/custom_store";

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss'],
  preserveWhitespaces: true,
})
export class OrderComponent implements OnInit {
  // dataSource: DataSource;
  dataSource: any = {};
  currentFood: any = new FoodData();
  foodData: any;

  foodData1: any[] = [{
    ID: 1,
    Name: 'Pizza',
    Class: 'Diamond',
    Images: [{
      Picture: 'assets/images/food/pizza.png',
    }, {
      Picture: 'assets/images/food/pizza2.png',
    }],
    Price: 299,
    Notes: 'John has been in the Audio/Video industry since 1990. He has led DevAv as its CEO since 2003. When not working hard as the CEO, John loves to golf and bowl. He once bowled a perfect game of 300.',
  }, {
    ID: 2,
    Name: 'Domates',
    Class: 'Platinum',
    Images: [{
      Picture: 'assets/images/food/domates.png',
    }],
    Price: 16,
    Notes: 'Olivia loves to sell. She has been selling DevAV products since 2012.  Olivia was homecoming queen in high school. She is expecting her first child in 6 months. Good Luck Olivia.',
  }, {
    ID: 3,
    Name: 'Pasta',
    Class: 'Gold',
    Images: [{
      Picture: 'assets/images/food/pasta.png',
    }],
    Price: 450,
    Notes: 'Robert was recently voted the CMO of the year by CMO Magazine. He is a proud member of the DevAV Management Team. Robert is a championship BBQ chef, so when you get the chance ask him for his secret recipe.',
  }, {
    ID: 4,
    Name: 'Süt',
    Class: 'Diamond',
    Images: [{
      Picture: 'assets/images/food/sut.png',
    }],
    Price: 23,
    Notes: "Greta has been DevAV's HR Manager since 2003. She joined DevAV from Sonee Corp. Greta is currently training for the NYC marathon. Her best marathon time is 4 hours. Go Greta.",
  }, {
    ID: 5,
    Name: 'Elma',
    Class: 'Diamond',
    Images: [{
      Picture: 'assets/images/food/elma.png',
    }],
    Price: 12,
    Notes: 'Brett came to DevAv from Microsoft and has led our IT department since 2012. When he is not working hard for DevAV, he coaches Little League (he was a high school pitcher).',
  }, {
    ID: 6,
    Name: 'Su',
    Class: 'Platinum',
    Images: [{
      Picture: 'assets/images/food/su.png',
    }],
    Price: 10,
    Notes: "Sandra is a CPA and has been our controller since 2008. She loves to interact with staff so if you've not met her, be certain to say hi. Sandra has 2 daughters both of whom are accomplished gymnasts.",
  }, {
    ID: 7,
    Name: 'Muz',
    Class: 'Gold',
    Images: [{
      Picture: 'assets/images/food/muz.png',
    }],
    Price: 30,
    Notes: 'Kevin is our hard-working shipping manager and has been helping that department work like clockwork for 18 months. When not in the office, he is usually on the basketball court playing pick-up games.',
  }, {
    ID: 8,
    Name: 'Patates',
    Class: 'Platinum',
    Images: [{
      Picture: 'assets/images/food/patates.png',
    }],
    Price: 20,
    Notes: 'Cindy joined us in 2008 and has been in the HR department for 2 years.  She was recently awarded employee of the month. Way to go Cindy!',
  }];

  constructor(private service: FoodService) {
    this.loadGrid();

    // this.dataSource = this.getDataSource(this.foodData1);
    // this.currentFood = this.getFirstFood(this.foodData1);
  }

  ngOnInit(): void {

  }

  loadGrid() {
    this.dataSource = new CustomStore({
      key: 'foodId',
      load: (loadOptions) => {
        return this.service.findAll(loadOptions).toPromise().then((response: any) => {
          console.log(response.data)
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


  selectionChanged = (e: any) => {
    this.currentFood = e.addedItems[0];
  };


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

  getDataSource(data: any): any {
    return new DataSource({
      store: new ArrayStore({
        data,
        key: 'ID',
      }),
      //group: 'Name',
      searchExpr: ['Name'],
    });
  }

  getFirstFood(data: any) {
    return data[0];
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

}


export class FoodData {
  ID: number | undefined;
  Name: string | undefined;
  Picture: string | undefined;
  Notes: string | undefined;
  Class: string | undefined;
  Price: number | undefined;
}



