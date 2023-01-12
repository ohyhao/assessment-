import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { lastValueFrom } from "rxjs";
import { Posting } from "../models";

@Injectable()
export class ListingService {

    constructor(private http: HttpClient) {}

    postListing(formData: FormData): Promise<Posting> {

        return lastValueFrom(
            this.http.post<Posting>('/api/posting', formData)
        )
    }

    putPosting(id: string, p: Posting): Promise<any> {

        return lastValueFrom(
            this.http.put<any>(`/api/posting/${id}`, p)
        )
    }
}