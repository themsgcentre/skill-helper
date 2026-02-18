import { Component } from '@angular/core';
import { IonApp } from '@ionic/angular/standalone';
import { LayoutComponent } from "./layout/layout.component";
import { TopBarComponent } from './top-bar/top-bar.component';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  imports: [IonApp, LayoutComponent],
})
export class AppComponent {
  constructor() {}
}
