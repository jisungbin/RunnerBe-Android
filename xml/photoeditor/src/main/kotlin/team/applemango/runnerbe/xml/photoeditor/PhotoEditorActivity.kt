/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [PhotoEditor.kt] created by Ji Sungbin on 22. 2. 11. 오후 8:57
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.xml.photoeditor

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import ja.burhanrashid52.photoeditor.OnSaveBitmap
import ja.burhanrashid52.photoeditor.PhotoEditorView
import ja.burhanrashid52.photoeditor.shape.ShapeBuilder
import ja.burhanrashid52.photoeditor.shape.ShapeType

class PhotoEditorActivity {

    private val defaultShapeBuilder = ShapeBuilder()
        .withShapeSize(60f)
        .withShapeType(ShapeType.LINE)
        .withShapeColor(Color.LTGRAY)
        .withShapeOpacity(100)

    private lateinit var editorActivity: PhotoEditorActivity

    fun init(context: Context, builder: ShapeBuilder.() -> ShapeBuilder = { defaultShapeBuilder }) {
        val layout = LayoutInflater.from(context)
            .inflate(R.layout.layout_photoeditor, ConstraintLayout(context), false)
        with(layout) {
            val editorView: PhotoEditorView = findViewById(R.id.editor)
            val editor = ja.burhanrashid52.photoeditor.PhotoEditor.Builder(context, editorView)
                .setPinchTextScalable(false)
                .build()
                .also {
                    it.setBrushDrawingMode(true)
                    it.setShape(builder(ShapeBuilder()))
                }

            val undo: Button = findViewById(R.id.undo)
            val redo: Button = findViewById(R.id.redo)
            val draw: Button = findViewById(R.id.draw)
            val export: Button = findViewById(R.id.export)
            val eraser: Button = findViewById(R.id.eraser)

            undo.setOnClickListener {
                editor.undo()
            }

            redo.setOnClickListener {
                editor.redo()
            }

            draw.setOnClickListener {
                editor.setBrushDrawingMode(true)
            }

            export.setOnClickListener {
                editor.saveAsBitmap(object : OnSaveBitmap {
                    override fun onBitmapReady(@NonNull saveBitmap: Bitmap?) {
                        Log.e("PhotoEditor", "Image Saved Successfully")
                    }

                    override fun onFailure(@NonNull e: Exception?) {
                        Log.e("PhotoEditor", "Failed to save Image")
                    }
                })
            }

            eraser.setOnClickListener {
                editor.brushEraser()
            }
        }
    }

    fun getInstance(): PhotoEditorActivity {
        return if (!::editorActivity.isInitialized) {
            throw UninitializedPropertyAccessException()
        } else {
            editorActivity
        }
    }
}
