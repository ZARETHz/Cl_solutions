package com.example.clsolutions.utils

import android.content.Context
import android.os.Environment

import com.example.clsolutions.model.Equipo

import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph

import java.io.File

fun generarPdfTicket(
    context: Context,
    id: String,
    equipo: Equipo
): File {

    val file = File(
        context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
        "ticket_$id.pdf"
    )

    val writer = PdfWriter(file)
    val pdf = PdfDocument(writer)
    val document = Document(pdf)

    document.add(Paragraph("CL SOLUTIONS"))
    document.add(Paragraph("TICKET DE REPARACIÓN"))
    document.add(Paragraph("------------------------"))

    document.add(Paragraph("Folio: $id"))
    document.add(Paragraph("Cliente: ${equipo.cliente}"))
    document.add(Paragraph("Teléfono: ${equipo.telefono}"))
    document.add(Paragraph("Marca: ${equipo.marca}"))
    document.add(Paragraph("Modelo: ${equipo.modelo}"))
    document.add(Paragraph("Falla: ${equipo.falla}"))
    document.add(Paragraph("Estado: ${equipo.estado}"))
    document.add(Paragraph("Fecha: ${equipo.fecha}"))

    document.add(Paragraph("------------------------"))
    document.add(Paragraph("Gracias por su preferencia"))

    document.close()

    return file
}