import { CartService } from './../../services/cart.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-car-status',
  templateUrl: './car-status.component.html',
  styleUrls: ['./car-status.component.css']
})
export class CarStatusComponent implements OnInit {
  totalPrice:number=0.00;
  totalQuantity:number=0;

  constructor(private cartService:CartService) { }

  ngOnInit(): void {
    this.updateCarStatus();
  }
  updateCarStatus() {
   // subscribe to the cart totalPrice
   this.cartService.totalPrice.subscribe((data)=>{
     this.totalPrice = data;
   })

   // subscribe to the cart totalQuantity
   this.cartService.totalQuantity.subscribe((data)=>{
     this.totalQuantity = data;
   })
  }

}
