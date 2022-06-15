import { PaymentInfo } from './../common/payment-info';
import { Observable } from 'rxjs';
import { Purchase } from './../common/purchase';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {
  private purchaseUrl = environment.ecommerceApiUrl + '/checkout/purchase'
  private paymentIntentUrl = environment.ecommerceApiUrl + '/checkout/payment-intent'

  constructor(private htppClient:HttpClient) { }

  placeOrder(purchase:Purchase):Observable<any>{
    return this.htppClient.post<Purchase>(this.purchaseUrl,purchase);
  }
  
  createPaymentIntent(paymentInfo:PaymentInfo):Observable<any>{
    return this.htppClient.post<PaymentInfo>(this.paymentIntentUrl,paymentInfo);
  }
}
