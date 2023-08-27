import {Component, OnInit, ViewChild} from '@angular/core';
import {DxDataGridComponent} from "devextreme-angular";

@Component({
  selector: 'app-food',
  templateUrl: './food.component.html',
  styleUrls: ['./food.component.scss']
})
export class FoodComponent implements OnInit {
  dataSource: any = [
    {
      ID: 1,
      FirstName: 'Savaş',
      LastName: 'Dede',
      BirthDate: '1964/03/16',
      Notes: 'John has been in the Audio/Video industry since 1990. He has led DevAv as its CEO since 2003.\r\n\r\nWhen not working hard as the CEO, John loves to golf and bowl. He once bowled a perfect game of 300.',
    }, {
      ID: 2,
      FirstName: 'Büşra',
      LastName: 'Peyton',
      BirthDate: '1981/06/03',
      Notes: 'Olivia loves to sell. She has been selling DevAV products since 2012. \r\n\r\nOlivia was homecoming queen in high school. She is expecting her first child in 6 months. Good Luck Olivia.',
    }, {
      ID: 3,
      FirstName: 'Hasan',
      LastName: 'Demir',
      BirthDate: '1974/09/07',
      Notes: 'Robert was recently voted the CMO of the year by CMO Magazine. He is a proud member of the DevAV Management Team.\r\n\r\nRobert is a championship BBQ chef, so when you get the chance ask him for his secret recipe.',
    },
  ];
  @ViewChild('dataGrid', {static: true}) dataGrid: DxDataGridComponent | undefined;

  constructor() {
  }

  ngOnInit(): void {
  }

}
