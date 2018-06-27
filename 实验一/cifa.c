#include<stdio.h>
#include<stdlib.h>
#include<string.h>

typedef struct Word
{
    char type[50];//����������
    char w[20];//��
} Word;

int main()
{
    char ch,word_temp[20]="";
    int i=0,j=0,key=0;
    char ktt[38][20]= {"int","char","void","if","else","switch","case","default","while","do","for","break","continue","return",
                       "+","-","*","/","%","++","--","!","&&","||","=",">",">=","<","<=","==","!=",
                       ",",":",";","(",")","{","}"
                      };
    char ktt1[38][20]= {"kw_int","kw_char","kw_void","kw_if","kw_else","kw_switch","kw_case","kw_default","kw_while","kw_do","kw_for","kw_break","kw_continue","kw_return",
                        "add","sub","mul","div","mod","inc","dec","not","and","or","assign","gt","ge","lt","le","equ","nequ",
                        "comma","colon","simcon","lparen","rparen","lbrac","rbrac"
                       };
    FILE *fp;
    Word word[500];
    fp = fopen("D:/1.c","r");
    if(!fp)
    {
        printf("can't open file\n");
        exit(1);
    }
    while((ch=fgetc(fp))!= EOF)
    {
        putchar(ch);
        if((ch>='a'&&ch<='z')||(ch>='A'&&ch<='Z')||(ch>='0'&&ch<='9'))
        {
            word_temp[key]=ch;//����������ĸ�����ɵ���
            word_temp[key+1]='\0';
            key++;
            continue;
        }
        if(strcmp(word_temp,"")!=0)
        {
            strcpy(word[i].w,word_temp);//�����ʿ������ṹ������
            strcpy(word_temp,"");
            key=0;//�ص���ʱ����Ŀ�ʼλ��
            i++;//�ṹ������±��1
            continue;
        }
        else if(ch==' '||ch==10||ch=='   ')   //ȥ���ո񣬻س���tab
        {
            continue;
        }
        else
        {
            word_temp[0]=ch;
            word_temp[1]='\0';
            strcpy(word[i].w,word_temp);//������ĸ���ַ��ſ������ṹ������
            strcpy(word_temp,"");
            key=0;//�ص���ʱ����Ŀ�ʼλ��
            i++;
        }
    }
    printf("\n�ʷ������������:\n");
    int k = 0;
    for(j=0; j<i; j++)
    {
        for(k=0; k<38; k++)
        {
            if((strcmp(word[j].w,ktt[k]))==0)
            {
                if(k>=0&&k<14)
                    strcpy(word[j].type,ktt1[k]);//�ؼ���
                else if(k>=14&&k<31)
                    strcpy(word[j].type,ktt1[k]);//�����
                else if(k>=31&&k<38)
                    strcpy(word[j].type,ktt1[k]);//�ֽ��
                break;
            }
            else if(word[j].w[0]>='0'&&word[j].w[0]<='9')
            {
                if(word[j].w[0] == '0')
                {
                    strcpy(word[j].type,"�˽�������");
                    if(word[j].w[1] == 'x')
                    {
                        strcpy(word[j].type,"ʮ����������");
                    }

                }
                else
                {
                    strcpy(word[j].type,"ʮ��������");
                }
            }
            else if(strlen(word[j].w) == 1)
            {

                strcpy(word[j].type,"ch");//�ַ�
            }
            else
            {
                strcpy(word[j].type,"str");//�ַ���
            }
        }
    }
    for(j=0; j<i; j++)
    {
        printf("(%s,'%s')\n",word[j].type,word[j].w);
    }

    fclose(fp);
    return 0;
}
