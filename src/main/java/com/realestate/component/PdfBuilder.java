package com.realestate.component;

import com.realestate.entity.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PdfBuilder {

    public String rentPdfContent(Transaction transaction) {
        return "<h2>Lease Agreement</h2>" +
                "<p>This Lease Agreement is made today, " + LocalDate.now() + " between:</p>" +
                "<ol>" +
                "<li><strong>OWNER:</strong> " + transaction.getAd().getOwner().getFirstName() + " " + transaction.getAd().getOwner().getLastName() + "</li>" +
                "<li><strong>TENANT:</strong> " + transaction.getCustomer().getFirstName() + " " + transaction.getCustomer().getLastName() + "</li>" +
                "</ol>" +
                "<h3>Purpose</h3>" +
                "<p>The property will be used by the tenant as a RESIDENTIAL property. " +
                "The purpose of the rented space cannot be changed.</p>" +
                "<h3>Duration</h3>" +
                "<p>This agreement is made for a period of 12 months, starting from the date of " + LocalDate.now() +
                " The tenant may extend the lease for the same period or a shorter one, " +
                "with the written consent of the owner, at least 30 days before the expiration date.</p>" +
                "<h3>Rental Price</h3>" +
                "<p>The agreed rental price for the property is <strong>" + transaction.getPrice() + " EUR per month</strong>.</p>" +
                "<h3>Owner's Obligations</h3>" +
                "<ul>" +
                "<li>The owner is responsible for ensuring that the property is free and remains so throughout the lease term.</li>" +
                "<li>The owner will provide the tenant with the property in good condition, along with a detailed inventory, signed by both parties.</li>" +
                "<li>The owner will pay all legal taxes for the property (property tax, income tax, etc.).</li>" +
                "<li>The owner will cover expenses for repairs to the common areas of the building.</li>" +
                "</ul>" +
                "<h3>Owner's Rights</h3>" +
                "<ul>" +
                "<li>The owner may visit the property whenever desired, with prior notice to the tenant and in the presence of the tenant.</li>" +
                "<li>The owner may accept or reject suggestions made by the tenant for modifications to the rented property, " +
                "with written consent from both parties.</li>" +
                "<li>The owner has the right to verify the tenant's payment of current obligations.</li>" +
                "</ul>" +
                "<h3>Tenant's Obligations</h3>" +
                "<ul>" +
                "<li>The tenant will use the property only for the purpose specified in this agreement.</li>" +
                "<li>The tenant may not sublet the property without written consent from the owner.</li>" +
                "<li>The tenant will pay all utilities (electricity, gas, water, trash) on time, " +
                "or if included in the maintenance fees, will ensure payment of the maintenance fees.</li>" +
                "<li>The tenant will maintain the property and the inventory in good condition.</li>" +
                "<li>The tenant will respect the building's regulations and rules.</li>" +
                "<li>The tenant will allow the owner access to the property at least once a month, with prior notice of 2 days.</li>" +
                "<li>The tenant will provide proof of payment for utilities when requested by the owner.</li>" +
                "<li>The tenant will deposit a security amount as agreed with the owner.</li>" +
                "<li>The tenant is responsible for any damage to the property caused by their actions.</li>" +
                "<li>The tenant will return the property in the condition it was at the start of the lease.</li>" +
                "</ul>" +
                "<h3>Tenant's Rights</h3>" +
                "<ul>" +
                "<li>The tenant has exclusive use of the property for the duration of the lease.</li>" +
                "<li>The tenant may make necessary improvements, as long as they do not alter the structural integrity of the property, " +
                "with the owner's prior approval.</li>" +
                "</ul>" +
                "<h3>Contract Terms:</h3>" +
                "<p>" + transaction.getContract().getTerms() + "</p>" +
                "<p>Signed on: " + LocalDate.now() + "</p>";
    }

    public String salePdfContent(Transaction transaction) {
        return "<h2>Sale Contract</h2>" +
                "<p>The property subject to this contract, as described above, has been in civil circulation since the date of acquisition, " +
                "has not been nationalized, has not been transferred in any form, does not constitute a contribution in kind or a workplace " +
                "within any commercial companies, associations, or foundations, has not been promised for sale or any other act of transfer " +
                "and has not been mortgaged, has not been the subject of any rental contract, loan or any other act of transferring the right of use, " +
                "there is no litigation in court regarding this property, it is not being claimed, and there are no requests made based on Laws no. 10/2001, " +
                "112/1995, 247/2005, or any other disputes regarding it. The property is free of any encumbrances or servitudes, as evidenced by the excerpt " +
                "from the land register. The Cadastre and Real Estate Publicity Office HomeStor.</p>" +

                "The registrations in the land registry will be carried out based on this authenticated sales contract, in accordance " +
                "with the provisions of Article 888 of the Civil Code." +

                "<p>We, the undersigned, declare that today, on the date of the notarial certification of this sale contract, the property is intended for residential use, " +
                "and the seller, " + transaction.getAd().getOwner().getFirstName() + " " + transaction.getAd().getOwner().getLastName() +
                ", has handed over to me in original the energy performance certificate for the property.</p>" +

                "<p>The entries in the land register will be made based on this sale contract concluded in authentic form, in accordance with article 888 of the Civil Code.</p>" +

                "<p>We, the contracting parties, have acknowledged the provisions of the Fiscal Code, article 1,660 of the Civil Code regarding the conditions of the sale price " +
                "(meaning it consists of a sum of money, is serious, and determined), the law on preventing and combating tax evasion, and the provisions of the Law " +
                "on preventing and sanctioning money laundering, with subsequent amendments.</p>" +

                "<p>I, " + transaction.getCustomer().getFirstName() + " " + transaction.getCustomer().getLastName() +
                ", as the buyer of the ownership rights over the above-mentioned property, undertake that within 30 days from the authentication " +
                "of this contract, I will present myself at the competent Directorate of Taxes and Local Taxes for the change of the fiscal record, in compliance with " +
                "legal provisions.</p>" +

                "Under the provisions of Article 111, paragraph 1 of the Fiscal Code, as amended by Emergency Ordinance 3/2017, this sales " +
                "contract is exempt from the payment of tax on income resulting from the transfer of ownership rights from the personal assets of natural persons." +

                "<p>All expenses related to the maintenance, electricity consumption, natural gas, water, or any other current payments regarding " +
                "the use of the property being transferred have been paid up to date by me, the seller, according to the existing invoices, " +
                "which I provide to the buyer along with proof of payment. I commit to fully settle all debts related to the use of the property " +
                "up to the moment of its effective handover, based on the invoices that will be provided to me by the buyer upon their receipt from the issuers.</p>" +

                "<h3>Sale Price:</h3>" +
                "<p>The sale price is <strong>" + transaction.getPrice() + "</strong> EUR.</p>" +

                "<h3>Contract Terms:</h3>" +
                "<p>" + transaction.getContract().getTerms() + "</p>" +
                "<p>Signed on: " + LocalDate.now() + "</p>";
    }

}
