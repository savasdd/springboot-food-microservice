import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
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
import {DevExtremeModule} from "devextreme-angular";
import {UserProfileComponent} from "./user-profile/user-profile.component";

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'profile', component: UserProfileComponent,
        data: {
          title: 'Profile'
        }
      },
    ]
  }
];

@NgModule({
  declarations: [
    UserProfileComponent,
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
  ]
})
export class UsersModule {
}
