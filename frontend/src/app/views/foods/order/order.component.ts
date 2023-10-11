import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit {

  employees: any[] = [{
    ID: 1,
    FirstName: 'Pizza',
    Picture: 'assets/images/food/pizza.png',
    Notes: 'John has been in the Audio/Video industry since 1990. He has led DevAv as its CEO since 2003. When not working hard as the CEO, John loves to golf and bowl. He once bowled a perfect game of 300.',
  }, {
    ID: 2,
    FirstName: 'Domates',
    Picture: 'assets/images/food/domates.png',
    Notes: 'Olivia loves to sell. She has been selling DevAV products since 2012.  Olivia was homecoming queen in high school. She is expecting her first child in 6 months. Good Luck Olivia.',
  }, {
    ID: 3,
    FirstName: 'Pasta',
    Picture: 'assets/images/food/pasta.png',
    Notes: 'Robert was recently voted the CMO of the year by CMO Magazine. He is a proud member of the DevAV Management Team. Robert is a championship BBQ chef, so when you get the chance ask him for his secret recipe.',
  }, {
    ID: 4,
    FirstName: 'Süt',
    Picture: 'assets/images/food/sut.png',
    Notes: "Greta has been DevAV's HR Manager since 2003. She joined DevAV from Sonee Corp. Greta is currently training for the NYC marathon. Her best marathon time is 4 hours. Go Greta.",
  }, {
    ID: 5,
    FirstName: 'Elma',
    Picture: 'assets/images/food/elma.png',
    Notes: 'Brett came to DevAv from Microsoft and has led our IT department since 2012. When he is not working hard for DevAV, he coaches Little League (he was a high school pitcher).',
  }, {
    ID: 6,
    FirstName: 'Su',
    Picture: 'assets/images/food/su.png',
    Notes: "Sandra is a CPA and has been our controller since 2008. She loves to interact with staff so if you've not met her, be certain to say hi. Sandra has 2 daughters both of whom are accomplished gymnasts.",
  }, {
    ID: 7,
    FirstName: 'Muz',
    Picture: 'assets/images/food/muz.png',
    Notes: 'Kevin is our hard-working shipping manager and has been helping that department work like clockwork for 18 months. When not in the office, he is usually on the basketball court playing pick-up games.',
  }, {
    ID: 8,
    FirstName: 'Patates',
    Picture: 'assets/images/food/patates.png',
    Notes: 'Cindy joined us in 2008 and has been in the HR department for 2 years.  She was recently awarded employee of the month. Way to go Cindy!',
  }];

  constructor() {
  }

  ngOnInit(): void {
  }

}
