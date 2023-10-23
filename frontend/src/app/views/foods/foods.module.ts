import {NgModule} from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {StockComponent} from './stock/stock.component';
import {
  ButtonGroupModule,
  ButtonModule,
  CardModule,
  CollapseModule,
  DropdownModule,
  FormModule,
  GridModule,
  NavbarModule,
  NavModule,
  SharedModule,
  UtilitiesModule
} from "@coreui/angular";
import {IconModule} from "@coreui/icons-angular";
import {ReactiveFormsModule} from "@angular/forms";
import {DocsComponentsModule} from "@docs-components/docs-components.module";
import {RouterModule, Routes} from "@angular/router";
import {FoodComponent} from './food/food.component';
import {PaymentComponent} from './payment/payment.component';
import {DevExtremeModule} from "devextreme-angular";
import {CategoryComponent} from './category/category.component';
import {OrderComponent} from './order/order.component';
import {ImagesComponent} from './food/images/images.component';
import {BasketComponent} from './order/basket/basket.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'stocks', component: StockComponent,
        data: {
          title: 'Stock'
        }
      },
      {
        path: 'foods', component: FoodComponent,
        data: {
          title: 'Food'
        }
      },
      {
        path: 'payments', component: PaymentComponent,
        data: {
          title: 'Payment'
        }
      },
      {
        path: 'categorys', component: CategoryComponent,
        data: {
          title: 'Category'
        }
      },
      {
        path: 'orders', component: OrderComponent,
        data: {
          title: 'Order'
        }
      },
      {
        path: 'baskets', component: BasketComponent,
        data: {
          title: 'Basket'
        }
      },
    ]
  }
];

@NgModule({
  declarations: [
    StockComponent,
    FoodComponent,
    PaymentComponent,
    CategoryComponent,
    OrderComponent,
    ImagesComponent,
    BasketComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ButtonModule,
    ButtonGroupModule,
    GridModule,
    IconModule,
    CardModule,
    UtilitiesModule,
    DropdownModule,
    SharedModule,
    FormModule,
    ReactiveFormsModule,
    DocsComponentsModule,
    NavbarModule,
    CollapseModule,
    NavModule,
    DevExtremeModule,
    NgOptimizedImage,
    FontAwesomeModule,
  ]
})
export class FoodsModule {
}
