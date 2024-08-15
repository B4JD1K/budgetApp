import { Component } from '@angular/core';
import {TestComponentComponent} from "./components/test-component/test-component.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    TestComponentComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {

}
