import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { SistemaSharedModule } from 'app/shared/shared.module';
import { SistemaCoreModule } from 'app/core/core.module';
import { SistemaAppRoutingModule } from './app-routing.module';
import { SistemaHomeModule } from './home/home.module';
import { SistemaEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    SistemaSharedModule,
    SistemaCoreModule,
    SistemaHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    SistemaEntityModule,
    SistemaAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class SistemaAppModule {}
