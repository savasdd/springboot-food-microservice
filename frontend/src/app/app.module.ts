import { NgModule } from '@angular/core';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { BrowserModule, Title } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';

import { NgScrollbarModule } from 'ngx-scrollbar';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

// Import containers
import { DefaultFooterComponent, DefaultHeaderComponent, DefaultLayoutComponent } from './containers';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

import {
  AvatarModule,
  BadgeModule,
  BreadcrumbModule,
  ButtonGroupModule,
  ButtonModule,
  CardModule,
  DropdownModule,
  FooterModule,
  FormModule,
  GridModule,
  HeaderModule,
  ListGroupModule,
  NavModule,
  ProgressModule,
  SharedModule,
  SidebarModule,
  TabsModule,
  UtilitiesModule,
} from '@coreui/angular';

import 'proj4leaflet';
import 'leaflet-fullscreen';
import 'leaflet-mouse-position';
import 'leaflet-realtime';
import 'leaflet-plugins/layer/tile/Yandex';
import 'leaflet-easybutton';
import 'leaflet.markercluster';
import 'leaflet-draw';
import 'leaflet-ajax';
import 'leaflet-iconmaterial';
import 'leaflet-measure-path';

import { IconModule, IconSetService } from '@coreui/icons-angular';
import { LoaderService } from "./auth/service/loader.service";
import { ErrorInterceptor } from "./auth/interceptors/error";
import { LoaderInterceptor } from "./auth/interceptors/loader";
import { ApiModule, Configuration } from "./services/gateway-service-api";
import { AuthGuard } from "./auth/guards/auth.guard";
import { BearerAuthInterceptor } from "./auth/interceptors/bearer-auth";

const APP_CONTAINERS = [
  DefaultFooterComponent,
  DefaultHeaderComponent,
  DefaultLayoutComponent
];

@NgModule({
  declarations: [AppComponent, ...APP_CONTAINERS],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    AvatarModule,
    BreadcrumbModule,
    FooterModule,
    DropdownModule,
    GridModule,
    HeaderModule,
    SidebarModule,
    IconModule,
    NavModule,
    ButtonModule,
    FormModule,
    UtilitiesModule,
    ButtonGroupModule,
    ReactiveFormsModule,
    SidebarModule,
    SharedModule,
    TabsModule,
    ListGroupModule,
    ProgressModule,
    BadgeModule,
    ListGroupModule,
    CardModule,
    NgScrollbarModule,
    HttpClientModule,
    FontAwesomeModule,
    // ApiModule.forRoot(() => {
    //   return new Configuration({
    //     basePath: `${environment.apiUrl}`
    //   });
    // }),
  ],
  providers: [
    LoaderService,
    // AuthService,
    // AuthGuard,
    IconSetService,
    Title,
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: LoaderInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: BearerAuthInterceptor, multi: true },
    // {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    { provide: LocationStrategy, useClass: HashLocationStrategy },
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
